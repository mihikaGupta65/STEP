
class BusRoute {

    private String routeCode;
    private String routeName;
    private int priority;

    BusRoute(String routeCode, String routeName, int priority) {
        this.routeCode = routeCode;
        this.routeName = routeName;
        this.priority = priority;
    }

    BusRoute(String routeCode, String routeName) {
        this(routeCode, routeName, 0);
    }

    int compareTo(BusRoute other) {

        if (this.priority != other.priority) {
            return Integer.compare(other.priority, this.priority);
        }

        int codeResult = this.routeCode.compareToIgnoreCase(other.routeCode);

        if (codeResult != 0) {
            return codeResult;
        }

        return this.routeName.compareToIgnoreCase(other.routeName);
    }

    static BusRoute[] rankRoutes(BusRoute[] routes) {

        if (routes == null) {
            return new BusRoute[0];
        }

        BusRoute[] ranked = routes.clone();

        for (int i = 0; i < ranked.length - 1; i++) {

            for (int j = 0; j < ranked.length - 1 - i; j++) {

                if (ranked[j].compareTo(ranked[j + 1]) > 0) {
                    BusRoute temp = ranked[j];
                    ranked[j] = ranked[j + 1];
                    ranked[j + 1] = temp;
                }
            }
        }

        return ranked;
    }

    String getRouteCode() {
        return routeCode;
    }
}

public class Pb3 {

    public static void main(String[] args) {

        BusRoute[] routes = {
            new BusRoute("RT205L", "Airport Express", 3),
            new BusRoute("rt201j", "City Central", 4),
            new BusRoute("RT299T", "Night Service")
        };

        BusRoute[] rankedRoutes = BusRoute.rankRoutes(routes);

        System.out.println("Ranked Bus Routes:");

        for (BusRoute route : rankedRoutes) {
            System.out.println(route.getRouteCode());
        }
    }
}
