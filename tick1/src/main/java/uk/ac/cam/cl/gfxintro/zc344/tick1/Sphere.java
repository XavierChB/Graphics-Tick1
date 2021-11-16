package uk.ac.cam.cl.gfxintro.zc344.tick1;

public class Sphere extends SceneObject {
    // Sphere coefficients
    private final double SPHERE_KD = 0.8;
    private final double SPHERE_KS = 1.2;
    private final double SPHERE_ALPHA = 10;
    private final double SPHERE_REFLECTIVITY = 0.3;

    // The world-space position of the sphere
    protected Vector3 position;

    public Vector3 getPosition() {
        return position;
    }

    // The radius of the sphere in world units
    private double radius;

    public Sphere(Vector3 position, double radius, ColorRGB colour) {
        this.position = position;
        this.radius = radius;
        this.colour = colour;

        this.phong_kD = SPHERE_KD;
        this.phong_kS = SPHERE_KS;
        this.phong_alpha = SPHERE_ALPHA;
        this.reflectivity = SPHERE_REFLECTIVITY;
    }

    public Sphere(Vector3 position,
                  double radius,
                  ColorRGB colour,
                  double kD,
                  double kS,
                  double alphaS,
                  double reflectivity) {
        this.position = position;
        this.radius = radius;
        this.colour = colour;

        this.phong_kD = kD;
        this.phong_kS = kS;
        this.phong_alpha = alphaS;
        this.reflectivity = reflectivity;
    }

    /*
     * Calculate intersection of the sphere with the ray. If the ray starts
     * inside the sphere, intersection with the surface is also found.
     */
    public RaycastHit intersectionWith(Ray ray) {
        // Get ray parameters
        Vector3 O = ray.getOrigin();
        Vector3 D = ray.getDirection();

        // Get sphere parameters
        Vector3 C = position;
        double r = radius;

        // Calculate quadratic coefficients
        double a = D.dot(D);
        double b = 2 * D.dot(O.subtract(C));
        double c = (O.subtract(C)).dot(O.subtract(C)) - Math.pow(r, 2);

        double det = b * b - 4.0 * a * c;
        if (det < 0) {
            return new RaycastHit();
        }

        double r1 = (-b + Math.sqrt(det)) / (2 * a);
        double r2 = (-b - Math.sqrt(det)) / (2 * a);
        double s = Math.min(r1, r2) > 0 ? Math.min(r1, r2) : Math.max(r1, r2);

        if (s > 0) {
            Vector3 positionHit = O.add(D.scale(s));
            return new RaycastHit(this,
                                  s,
                                  positionHit,
                                  this.getNormalAt(positionHit));
        }
        return new RaycastHit();
    }

    // Get normal to surface at position
    public Vector3 getNormalAt(Vector3 position) {
        return position.subtract(this.position).normalised();
    }
}
