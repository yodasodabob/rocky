/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rockstar.statement;

import rockstar.expression.ConstantExpression;
import rockstar.expression.PlusExpression;
import rockstar.expression.VariableReference;
import rockstar.runtime.BlockContext;
import rockstar.runtime.RockstarRuntimeException;
import rockstar.runtime.Value;

/**
 *
 * @author Gabor
 */
public class IncrementStatement extends Statement {

    private final VariableReference variable;
    private final int count;
    private PlusExpression plus;

    public IncrementStatement(VariableReference variable, int count) {
        this.variable = variable;
        this.count = count;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\n    " + variable + " ++".repeat(count);
    }

    private PlusExpression getPlus() {
        if (plus == null) {
            plus = new PlusExpression();
            plus.addParameter(variable);
            plus.addParameter(new ConstantExpression(count));
        }
        return plus;
    }

    @Override
    public void execute(BlockContext ctx) {
        Value v = ctx.getVariableValue(variable.getName());
        if (v.isNumeric()) {
            // increment by count
            Value value = getPlus().evaluate(ctx);
            ctx.setVariable(variable.getName(), value);
        } else if (v.isBoolean()) {
            // convert to boolean
            v = v.asBoolean();
            if (count % 2 == 1) {
                // negate boolean
                v = v.negate();
            }
            ctx.setVariable(variable.getName(), v);
        }
        throw new RockstarRuntimeException(v.getType() + " ++");
    }

    @Override
    public String explain(BlockContext ctx) {
        return variable.getName() + " = " + ctx.getVariableValue(variable.getName());
    }
}
