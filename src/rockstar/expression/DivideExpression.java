/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rockstar.expression;

import rockstar.runtime.BlockContext;
import rockstar.runtime.Value;

/**
 *
 * @author Gabor
 */
public class DivideExpression extends CompoundExpression {

    @Override
    public String getFormat() {
        return "(%s / %s)";
    }

    @Override
    public int getPrecedence() {
        return 400;
    }
    
    @Override
    public int getParameterCount() {
        return 2;
    }
    
    @Override
    public Value evaluate(BlockContext ctx) {
        ctx.beforeExpression(this);
        Expression expr1 = this.getParameters().get(0);
        Expression expr2 = this.getParameters().get(1);
        Value v1 = expr1.evaluate(ctx);
        Value v2 = expr2.evaluate(ctx);
        return ctx.afterExpression(this, v1.divide(v2));
    }    
}
