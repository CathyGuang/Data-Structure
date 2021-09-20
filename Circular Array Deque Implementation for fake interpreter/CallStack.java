import java.lang.UnsupportedOperationException;
/**
*CallBack.java
*Extends CircularArrayDequeImplementation.
*Override the method addBack, removeBack, peekBack, addFront, and removeFront
*to adapt to store Method object
*/
public class CallStack extends CircularArrayDequeImplementation<Method>{
    SimulationComponent sComponent;
    
    
    public CallStack(SimulationComponent a){
        super();
        sComponent = a;
    }
    
    /** 
     * addBack throws an error because we only use stack method
     */
    public void addBack(Method method){
        throw new UnsupportedOperationException();
    }
    
    /** 
     * removeBack throws an error because we only use stack method
     */
    public Method removeBack(){
        throw new UnsupportedOperationException();
    }
    
    /** 
     * peekBack throws an error because we only use stack method
     */
    public Method peekBack(){
        throw new UnsupportedOperationException();
    }
    
    /** 
     * addFront add the Method to the array
     */
    public void addFront(Method method){
        super.addFront(method);
        sComponent.addMethodToGraphicalStack(method);
    }
    
    /** 
     * removeFront removes the Method from the array
     */
    public Method removeFront(){
        Method method = super.removeFront();
        sComponent.removeMethodFromGraphicalStack(method);
        return method;
    }
}