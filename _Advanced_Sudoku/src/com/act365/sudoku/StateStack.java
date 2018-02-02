/*
 * Su Doku Solver
 * 
 * Copyright (C) act365.com August 2005
 * 
 * Web site: http://act365.com/sudoku
 * E-mail: developers@act365.com
 * 
 * The Su Doku Solver solves Su Doku problems - see http://www.sudoku.com.
 * 
 * This program is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by the Free 
 * Software Foundation; either version 2 of the License, or (at your option) 
 * any later version.
 *  
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General 
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with 
 * this program; if not, write to the Free Software Foundation, Inc., 
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package com.act365.sudoku ;

import java.util.ArrayList ;

/**
 * StateStack stores state grids in a dynamically-expanding ArrayList.
 * The class should be used for memory-intensive state grids that
 * are rarely references (so that performance isn't critical), such
 * as LinearSystemState.
 */

// RR - Changed StateStack to extend generic ArrayList of Objects instead a raw Vector
public class StateStack extends ArrayList<Object> {

    int nMovesStored ;
    
    int[] moves ;
    
    /**
     * Creates a StateStack to store at most maxMoves moves. 
     */

    public StateStack( int maxMoves ){
        nMovesStored = 0 ;
        moves = new int[maxMoves];
        //setSize( maxMoves );
    }
    
    /**
     * @see com.act365.sudoku.IState#pushState(int)
     */
     
    public void pushState( Object obj , int nMoves ) {
        int i = 0 ;
        while( i < nMovesStored && moves[i] != nMoves ){
            ++ i ;   
        }
        if( i < nMovesStored ){
           // setElementAt( obj , i ); 
            add(i, obj);
        } else {
            //addElement( obj );
        		add(obj);
            moves[nMovesStored++] = nMoves ;
        }
    }    

    /**
     * @see com.act365.sudoku.IState#popState(int)
     */
        
    public Object popState( int nMoves ) {
        int i = 0 ;
        while( i < nMovesStored && moves[i] != nMoves ){
            ++ i ;   
        }
        if( i < nMovesStored ){
            //return elementAt( i );
        		return get(i);
        } else {
            return null ;   
        }
    }
}
