/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rockstar.statement;

import rockstar.runtime.BlockContext;
import rockstar.runtime.RockstarRuntimeException;

/**
 *
 * @author Gabor
 */
public class BlockEnd extends Statement {

    @Override
    public void execute(BlockContext ctx) {
        throw new RockstarRuntimeException("Not supported.");        
        
    }
    
    @Override
    public String explain(BlockContext ctx) {
        return null;
    }
}
