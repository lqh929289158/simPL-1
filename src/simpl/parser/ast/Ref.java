package simpl.parser.ast;

import simpl.interpreter.RefValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.RefType;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Ref extends UnaryExpr {

    public Ref(Expr e) {
        super(e);
    }

    public String toString() {
        return "(ref " + e + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult l_type = e.typecheck(E);
        return TypeResult.of(l_type.s,new RefType(l_type.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value v = e.eval(s);
        int current_p = s.p.get();
        s.M.put(current_p, v);
        s.p.set(current_p + 4);
        return new RefValue(current_p);
    }
}
