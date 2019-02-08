/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rockstar.statement;

import rockstar.expression.ConstantExpression;
import rockstar.expression.MinusExpression;
import rockstar.expression.VariableReference;
import rockstar.runtime.BlockContext;
import rockstar.runtime.NumericValue;
import rockstar.runtime.RockstarRuntimeException;
import rockstar.runtime.Value;

/**
 *
 * @author Gabor
 */
public class DecrementStatement extends Statement {

    private final VariableReference variable;
    private final int count;
    private MinusExpression minus;

    public DecrementStatement(VariableReference variable, int count) {
        this.variable = variable;
        this.count = count;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\n    " + variable + " --".repeat(count);
    }

    private MinusExpression getMinus() {
        if (minus == null) {
            minus = new MinusExpression();
            minus.addParameter(variable);
            minus.addParameter(new ConstantExpression(count));
        }
        return minus;
    }

    @Override
    public void execute(BlockContext ctx) {
        Value v = ctx.getVariableValue(variable.getName());
        if (v.isMysterious() || v.isNull()) {
            v = Value.getValue(NumericValue.ZERO);
            // v is set to a numeric value
            ctx.setVariable(variable.getName(), v);
        }
        if (v.isNumeric()) {
            // increment by count
            Value value = getMinus().evaluate(ctx);
            ctx.setVariable(variable.getName(), value);
            return;
        } else if (v.isBoolean()) {
            // convert to boolean
            v = v.asBoolean();
            if (count % 2 == 1) {
                // negate boolean
                v = v.negate();
            }
            ctx.setVariable(variable.getName(), v);
            return;
        }
        throw new RockstarRuntimeException(v.getType() + " ++");
    }
    
    @Override
    public String explain(BlockContext ctx) {
        return variable.getName() + " = " + ctx.getVariableValue(variable.getName());
    }
    
    @Override
    protected String list() {
        return variable.getName()+ " -= " + count;
    }
}
