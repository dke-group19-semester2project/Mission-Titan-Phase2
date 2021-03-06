public class WindSpeedStochImplentationEx{
    //Date starting on January 1,2000

    public static void main(String[] args){
        final double MISSION_DURATION=1;
        final double TIME_STEP=1;//In seconds

        //Wind
        WindSpeedStochastic titanWindSpeed= new WindSpeedStochastic(TIME_STEP);
        //For testing only. Initalized position.
        Vector2D position= new Vector2D(300, 400);
        Vector2D velocity = new Vector2D(0,0);
        for (int i = 0; i<MISSION_DURATION; i++) {
             //Test data. 
             position.setX(-2.56E6+10E3);
             position.setY(-2.56E6);

             
             //Updates wind model and computes drag and lift.
             //Drag is the x, lift is the y. 
             titanWindSpeed.setRandomParameter(0.2);//0 for most deterministic, 1 for the most random.
             Vector2D forces=titanWindSpeed.updateModelAndGetDrag(position,velocity);
             System.out.println(forces.getX() + " " + forces.getY());
            }
        }
    }
    
    