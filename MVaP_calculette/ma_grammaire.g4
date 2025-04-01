grammar ma_grammaire;

@members{
    private int _cur_label = 1;
    private String newLabel() { return "Label" + (_cur_label++); }
    private TablesSymboles tablesSymboles = new TablesSymboles();
}

start : calcul EOF;
calcul returns [ String code ]
@init{ $code = new String(); }   // On initialise $code, pour ensuite l'utiliser comme accumulateur
@after{ System.out.println($code); }
    :   (decl { $code += $decl.code; })*
        { $code += "  JUMP Start\n"; }
        NEWLINE*

        (fonction { $code += $fonction.code; })*
        NEWLINE*

        { $code += "LABEL Start\n"; }
        (instruction { $code += $instruction.code; })*

        { $code += "  HALT\n"; }
    ;

instruction returns [String code]
    : expression finInstruction { $code = $expression.code; }
    | finInstruction { $code = ""; }
    | assignation finInstruction { $code = $assignation.code; }
    | input finInstruction { $code = $input.code; }
    | output finInstruction { $code = $output.code; }
    | whileLoop { $code = $whileLoop.code; }
    | branchement{ $code = $branchement.code; }
    | forLoop { $code = $forLoop.code; }
    | bloc { $code = $bloc.code; }
    | RETURN expression finInstruction    
        {
            $code = $expression.code;
            $code += "STOREL " + tablesSymboles.getReturn().address + "\n";
            $code += "RETURN\n";
        }
    ;

expression returns [String code, String type]
    : '-' expression { $code = $expression.code + "PUSHI -1\nMUL\n"; 
                       $type = $expression.type;}
    | '(' expression ')' { $code = $expression.code; 
                           $type = $expression.type;}
    | a = expression op = ('*' | '/' | '%') b = expression
        { 
            if($a.type.equals("double") || $b.type.equals("double")){
                $type = "double";
            }else{
                $type = "int";
            }
            boolean estInt = $type.equals("int");
            $code = $a.code;
            if(!estInt){
                if(!$type.equals($a.type)){ 
                    $code += "ITOF\n";
                }
            }
            $code += $b.code;
            if(!estInt){
                if(!$type.equals($b.type)){ 
                    $code += "ITOF\n";
                }
            }
            
            $code += ($op.text.equals("*") ? "MUL\n" : $op.text.equals("/") ? "DIV\n" : "MOD\n"); 
            
        }
    | a = expression op = ('+' | '-') b = expression
        { 
            if($a.type.equals("double") || $b.type.equals("double")){
                $type = "double";
            }else{
                $type = "int";
            }

            boolean estInt = $type.equals("int");
            $code = $a.code;
            if(!estInt){
                if(!$type.equals($a.type)){ 
                    $code += "ITOF\n";
                }
            }
            $code += $b.code;
            if(!estInt){
                if(!$type.equals($b.type)){ 
                    $code += "ITOF\n";
                }
            }
            $code += ($op.text.equals("+") ? "ADD\n" : "SUB\n"); 
            
        } 
    | IDENTIFIANT '(' args ')'                  // Appel de fonction
        {
            String functionType = tablesSymboles.getFunction($IDENTIFIANT.text);
            $type = functionType;
            if($type.equals("int")){
                $code = "PUSHI 0\n";
            }else{
                $code = "";
            }
            
            if($args.size > 0){
                $code += $args.code; // on empile les valeurs pour les arguments
                $code += "CALL ";
            }else{
                $code += "CALL ";
            }
            
            $code += $IDENTIFIANT.text + "\n";
            for(int i = 0; i < $args.size; i++){ // on supprime les arguments de la fonction
                $code += "POP\n";
		    }
        }
    | ENTIER { $code = "PUSHI " + $ENTIER.text + "\n"; 
                $type = "int";}
    | DOUBLE { $code = "PUSHF " + $DOUBLE.text + "\n"; 
                $type = "double";}
    | IDENTIFIANT
        { VariableInfo vi = tablesSymboles.getVar($IDENTIFIANT.text);
          $type = vi.type;
          $code = vi.address>=0 ? "PUSHG " : "PUSHL "; 
          $code += vi.address + "\n"; 
          if(vi.type.equals("double")) {
			$code += vi.address+1>=0 ? "PUSHG " : "PUSHL "; 
            $code += vi.address+1 + "\n"; 
		  }
        }
    ;

decl returns [String code]
    : TYPE IDENTIFIANT 
        { 
            tablesSymboles.addVarDecl($IDENTIFIANT.text, $TYPE.text);
            $code = $TYPE.text.equals("int")?"PUSHI 0\n":"PUSHF 0.0\n"; 
        }
        ('=' expression 
            { 
                boolean estInt = $TYPE.text.equals("int");
                $code += $expression.code; 
                if(!estInt){
                    if(!$TYPE.text.equals($expression.type)){ 
                        $code += "ITOF\n";
				    }
                    $code += "STOREG " + (tablesSymboles.getVar($IDENTIFIANT.text).address + 1) + "\n"; 
                }else{
                    if($expression.type.equals("double")){ 
                        $code += "  FTOI\n";
                    }
                }
                $code += "STOREG " + tablesSymboles.getVar($IDENTIFIANT.text).address + "\n"; 
            }
        )?
        (
            ',' IDENTIFIANT
                {
                    tablesSymboles.addVarDecl($IDENTIFIANT.text, $TYPE.text);
                    $code = $TYPE.text.equals("int")?"PUSHI 0\n":"PUSHF 0.0\n"; 
                }
                ('=' expression 
                    { 
                        boolean estInt = $TYPE.text.equals("int");
                        $code += $expression.code; 
                        if(!estInt){
                            if(!$TYPE.text.equals($expression.type)){ 
                                $code += "ITOF\n";
                            }
                            $code += "STOREG " + (tablesSymboles.getVar($IDENTIFIANT.text).address + 1) + "\n"; 
                        }else{
                            if($expression.type.equals("double")){ 
                                $code += "  FTOI\n";
                            }
                        }
                        $code += "STOREG " + tablesSymboles.getVar($IDENTIFIANT.text).address + "\n"; 
                    }
                )?
        )*
        finInstruction
    ;

assignation returns [String code]
    : IDENTIFIANT '=' expression
        { VariableInfo vi = tablesSymboles.getVar($IDENTIFIANT.text);
          boolean estInt = vi.type.equals("int");
          $code = $expression.code ;
          if(!estInt){
                if(!vi.type.equals($expression.type)){ 
                    $code += "ITOF\n";
                }
                $code += (vi.address >= 0 ? "STOREG " : "STOREL ") + (vi.address + 1) + "\n";
            }else{
                if($expression.type.equals("double")){ 
                    $code += "  FTOI\n";
                }
            }
          $code += vi.address >= 0 ? "STOREG " : "STOREL "; 
          $code += vi.address + "\n"; }
    | IDENTIFIANT op = ('+='|'-='|'*='|'/=') expression
        { VariableInfo vi = tablesSymboles.getVar($IDENTIFIANT.text);
          boolean estInt = vi.type.equals("int");
          $code = $code = vi.address >= 0 ? "PUSHG " : "PUSHL "; 
          $code += vi.address + "\n";
          if(!estInt){
                $code += (vi.address >= 0 ? "PUSHG " : "PUSHL ") + (vi.address + 1) + "\n";
            }
          $code += $expression.code;
          if(!estInt){
                if(!vi.type.equals($expression.type)){ 
                    $code += "ITOF\n";
                }
            }else{
                if($expression.type.equals("double")){ 
                    $code += "  FTOI\n";
                }
            }
          if($op.text.contains("*")){
                $code += "MUL\n";
            } else if($op.text.contains("/")){
                $code += "DIV\n";
            } else if($op.text.contains("+")){
                $code += "ADD\n";
            } else {
                $code += "SUB\n";
            }

           if(!estInt){
                $code += (vi.address >= 0 ? "STOREG " : "STOREL ") + (vi.address + 1) + "\n";
            }
          $code += vi.address >= 0 ? "STOREG " : "STOREL "; 
          $code += vi.address + "\n";  }
    ;

input returns [String code]
    : 'input' '(' IDENTIFIANT ')'
        { VariableInfo vi = tablesSymboles.getVar($IDENTIFIANT.text);
          $code = vi.type.equals("int")?"READ\n":"READF\n";
          $code += (vi.type.equals("double")?"STOREG " + (vi.address+1) : "")+"\n";
          $code += "STOREG " + vi.address + "\n";  
        }
    ;

output returns [String code]
    : 'output' '(' expression ')'
        { 
          $code = $expression.code + ($expression.type.equals("int")?"WRITE\n":"WRITEF\n");
          $code += $expression.type.equals("double")?"POP\nPOP\n":"POP\n"; 
        }
    ;

finInstruction : (NEWLINE | ';')+;


bloc returns [String code] @init{ $code = new String(); } 
    : NEWLINE* '{' (instruction { $code += $instruction.code; })* '}'  NEWLINE*;

operateurConditionnel returns [String code]
    :
	  '==' { $code = "EQUAL\n"; }
	| '!=' { $code = "NEQ\n"; }
    | '<>' { $code = "NEQ\n"; }
	| '<' { $code = "INF\n"; }
	| '<=' { $code = "INFEQ\n"; }
	| '>' { $code = "SUP\n"; }
	| '>=' { $code = "SUPEQ\n"; }
    ;

condition returns [String code]
    : 'True' { $code = "PUSHI 1\n"; }
    | 'False' { $code = "PUSHI 0\n"; }
    | '(' condition ')' { $code = $condition.code; }
    | 'not' a = condition
        { $code = $a.code + "PUSHI 0\nEQUAL\n"; }
    | a = condition op = 'and' b = condition
        { 
            $code = $a.code + $b.code + "MUL\n";
        }
    | a = condition op = 'or' b = condition
        {
            $code = $a.code + $b.code + "ADD\nPUSHI 0\nSUP\n";
        }
    | c = expression o = operateurConditionnel d = expression
        {   
            String type = "";
            if($c.type.equals("double") || $d.type.equals("double")){
                type = "double";
            }else{
                type = "int";
            }

            boolean estInt = type.equals("int");
            $code = $c.code;
            if(!estInt){
                if(!type.equals($c.type)){ 
                    $code += "ITOF\n";
                }
            }
            
            $code += $d.code;
            if(!estInt){
                if(!type.equals($d.type)){ 
                    $code += "ITOF\n";
                }
            }
            $code += $o.code;
        }
    ;


whileLoop returns [String code]
    : 'while' '(' cond = condition ')' instruction
        { String startLabel = newLabel();
          String endLabel = newLabel();
          $code = "LABEL " + startLabel + "\n" + $cond.code + "JUMPF " + endLabel + "\n" + $instruction.code + "JUMP " + startLabel + "\n" + "LABEL " + endLabel + "\n"; }
    ;

branchement returns [String code]
    : 'if' '(' c=condition ')' 'then'? blocthen=instruction ('else' blocelse=instruction)?  
    {
        String endLabel = newLabel();
        String elseLabel = newLabel();
        
        // Vérification propre pour éviter les erreurs
        String elseCode = ($blocelse.ctx != null) ? $blocelse.code : "";

        $code = $c.code + "JUMPF " + elseLabel + "\n" + 
                $blocthen.code + 
                (!elseCode.isEmpty() ? "JUMP " + endLabel + "\nLABEL " + elseLabel + "\n" + elseCode + 
                "LABEL " + endLabel + "\n": "LABEL " + elseLabel + "\n"); 
    }
    ;

forLoop returns [String code]
    : 'for' '(' init = assignation ';' cond = condition ';' update = assignation ')' instruction
    {
        String startLabel = newLabel();
        String endLabel = newLabel();

        // Génération du code pour la whileLoop
        $code = $init.code + "LABEL " + startLabel + "\n" + $cond.code + "JUMPF " + endLabel + "\n" + $instruction.code + $update.code + "JUMP " + startLabel + "\n" + "LABEL " + endLabel + "\n";      
    }
    ;

fonction returns [ String code ]
        @init {
            tablesSymboles.enterFunction();
        }
        @after {
            tablesSymboles.exitFunction();
        }
    : TYPE IDENTIFIANT
        {
            //  Enregistre le type de la fonction 
            tablesSymboles.addFunction($IDENTIFIANT.text,$TYPE.text);
            $code = "LABEL " + $IDENTIFIANT.text + "\n";
            
        }
        '('  params ? ')' 
        '{'  NEWLINE?
        
                (decl { $code += $decl.code; })*

        NEWLINE*

         (  instruction  { $code += $instruction.code; } )*

        '}' 

        NEWLINE*

         { $code += "RETURN\n";} 
    ;

params
    : TYPE IDENTIFIANT
        {
            // code java gérant le premier paramètre
            tablesSymboles.addParam($IDENTIFIANT.text,"int");
        }
        ( ',' TYPE IDENTIFIANT
            {
                // code java gérant un paramètre
                tablesSymboles.addParam($IDENTIFIANT.text,"int");
            }
        )*
    ;

args returns [ String code, int size] 
    @init{ $code = new String(); $size = 0; }
    : ( expression 
    {
        // code java pour première expression pour arg
        $code = $expression.code;
        $size ++;
        
    }
    ( ',' expression
    {
        // code java pour expression suivante pour arg
        $code += $expression.code;
        $size ++;
    }
    )*
      )?
    ;

// lexer

RETURN: 'return';

TYPE : 'int' | 'double' ;

IDENTIFIANT : ('a'..'z' | 'A'..'Z' | '_')('a'..'z' | 'A'..'Z' | '_' | '0'..'9')*;

NEWLINE 
    : ('#' ~('\n'|'\r')*)? '\r'? '\n' ; // Gérer les sauts de ligne, incluant les commentaires

WS
    : (' ' | '\t')+ -> skip; // Espaces et tabulations à ignorer

ENTIER
    : ('0'..'9')+ ; // Entiers

fragment EXPOSANT: ('e' | 'E') ('+' | '-')? ENTIER;
DOUBLE: ENTIER (('.') ('0' ..'9')*)? EXPOSANT?;

COMMENT_SINGLE
    : '//' ~[\r\n]* -> skip; // Commentaires à une seule ligne

COMMENT_MULTI
    : '/*' .*? '*/' -> skip; // Commentaires multi-lignes

UNMATCH
    : . -> skip; // Tout autre caractère non prévu est ignoré

