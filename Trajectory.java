import java.util.ArrayList;

public class Trajectory{

    private SolarSystem solarSystem = new SolarSystem();

    /**
     * This method calculates the spaceprobe with the trajectory towards the goal from a certain source
     * @param source is the initial position of which the probe gets launched
     * @param goal is the goal which is desired to be reached
     * @param yearsTime how many years should be considered to reach the goal
     * @return returns a spaceprobe which has the inital velocity to reach the goal from the source
     */
    public SpaceProbe gradientDescend(SpaceObject source, SpaceObject goal, double yearsTime){
        double swarmspeed = 10E7;
        Boolean stop = false;
        SpaceProbe localBestProbe = new SpaceProbe(source.getPosition().clone().addConstant(source.getRadius()), source.getVelocity().clone(),5000);
        SpaceProbe globalBestProbe = localBestProbe.clone();

        while (!stop){
            System.out.println(swarmspeed);
            ArrayList<SpaceProbe> probesList = new ArrayList<>();
            probesList.addAll(createSwarm(localBestProbe, swarmspeed));
            solarSystem = solarSystem.reset();
            solarSystem.objectList.addAll(probesList);


            for (int i = 0; i < (int) (yearsTime * 365 * 86400 / 60); i++) {
                solarSystem.updateSolarSystem(60);
                for (SpaceProbe p : probesList) {
                    p.calcMinDist(goal);
                }
            }
            localBestProbe = bestProbe(probesList);
            System.out.println(localBestProbe);
            if (localBestProbe.getMinDist() < goal.getRadius()){
                stop = true;
                globalBestProbe = localBestProbe.clone();
                return globalBestProbe;
            }
            if (localBestProbe.getMinDist() < globalBestProbe.getMinDist()){
                globalBestProbe = localBestProbe.clone();
            }
            swarmspeed *= 0.7;
            if (swarmspeed < 1){
                return globalBestProbe;
            }
            Vector3D bestVelocity = globalBestProbe.getVelocity().clone();
            localBestProbe.reset();
            localBestProbe.setVelocity(bestVelocity.clone());
        }
        return globalBestProbe;
    }

    public SpaceProbe earthToTitan(double yearsTime){
        return gradientDescend(solarSystem.earth,solarSystem.titan,yearsTime);
    }

    public SpaceProbe titanToEarth(double yearsTime){
        return gradientDescend(solarSystem.titan,solarSystem.earth,yearsTime);
    }

    /**
     * This method creates a swarm around a probe
     * @param probe center of the swarm
     * @param swarmspeed modifications to the speed of the swarm around the center
     * @return returns a list containing the swarm of spaceprobes
     */
    private ArrayList<SpaceProbe> createSwarm(SpaceProbe probe, double swarmspeed) {

        ArrayList<SpaceProbe> probesList = new ArrayList<SpaceProbe>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    probesList.add(probe.cloneChange(x * swarmspeed, y * swarmspeed, z * swarmspeed));
                }
            }
        }
        return probesList;
    }

    /**
     *
     * @param probeList represents the swarm
     * @return returns the probe with the shortest distance towards the goal they were assigned
     */
    private SpaceProbe bestProbe(ArrayList<SpaceProbe> probeList) {
        double min = Double.MAX_VALUE;
        SpaceProbe best = null;
        for (SpaceProbe p : probeList) {
            if (p.getMinDist() < min) {
                min = p.getMinDist();
                best = p;
            }
        }
        return best;
    }
}