public class WindSpeedImplentationEx{
    //Date starting on January 1,2000
    public static void main(String[] args){
        final double MISSION_DURATION=1;
        final double TIME_STEP=1;//In seconds
        //Wind
        WindSpeed titanWindSpeed= new WindSpeed(TIME_STEP);
        //For testing only. Initalized position.
        Vector2D position= new Vector2D(300, 400);
        for (int i = 0; i<MISSION_DURATION; i++) {
                //Test data. 
                position.setX(3.375E6);
                position.setY(0);
                //Updates wind model and computes drag and lift.
                //Drag is the x, lift is the y. 
                Vector2D forces=titanWindSpeed.updateModelAndGetDrag(position);
            }
        }
    }