import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClosetPoint {

static List<Point> closestPoint;
    public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            int n = scanner.nextInt();
            List<Point> points = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                long x = scanner.nextInt();
                long y = scanner.nextInt();
                points.add(new Point(x, y));
            }
            MergeSort sortClass = new MergeSort();
            points.forEach((point) -> {
                long t = point.x;
                point.x = point.y;
                point.y = t;
            });
            List<Point> ySorted = sortClass.sort(points);
            ySorted.forEach((point) -> {
                long t = point.x;
                point.x = point.y;
                point.y = t;
            });
            List<Point> xSorted = sortClass.sort(points);
            Point ans = findClosetPoints(xSorted, ySorted);
            if (ans.x<ans.Closest.x){
                System.out.println(ans.x + " " + ans.y + " " + ans.Closest.x + " " + ans.Closest.y);
            }else {
                System.out.println(ans.Closest.x + " " + ans.Closest.y+" "+ans.x + " " + ans.y);
            }
        }

    private static Point findClosetPoints(List<Point> xSorted, List<Point> ySorted) {
        if (xSorted.size() < 4) {
            return findMinDistanceBruteForce(xSorted);
        }
        int m = xSorted.size() / 2;
        Point midPoint = xSorted.get(m);

        Point leftMin = findClosetPoints(xSorted.subList(0, m), ySorted.subList(0, m));
        Point rightMin = findClosetPoints(xSorted.subList(m, xSorted.size()), ySorted.subList(m, xSorted.size()));

        Point min = leftMin.minDistance > rightMin.minDistance? rightMin: leftMin;


        int stripLeft = -1;
        int stripRight = -1;
        for (int i = 0; i < xSorted.size(); i++) {
            if (Math.abs(ySorted.get(i).x - midPoint.x) < min.minDistance) {
                if (stripLeft == -1) {
                    stripLeft = i;
                } else {
                    stripRight = i;
                }
            }
        }
        Point minFromStrip = getMinStripeDistance(ySorted, stripLeft, stripRight);
        return min.minDistance > minFromStrip.minDistance ? minFromStrip : min;
    }

    private static Point getMinStripeDistance(List<Point> ySorted, int stripLeft, int stripRight) {
        Point min = new Point(0,0);
        min.minDistance=Double.MAX_VALUE;
        int bound=(stripRight-stripLeft)/2;
        for (int i = Math.max(bound-3,0); i <= Math.min(bound+3, ySorted.size()-1); i++) {
            for (int j = i + 1; j <= Math.min(bound+3, ySorted.size()-1); j++) {
                if (ySorted.get(i).getDistance(ySorted.get(j)) < min.minDistance) {
                    min=ySorted.get(i);
                    min.minDistance = ySorted.get(i).getDistance(ySorted.get(j));
                    min.Closest=ySorted.get(j);

                }
            }
        }
        return min;
    }


    private static Point findMinDistanceBruteForce(List<Point> points) {
        Point min = new Point(0,0);
        min.minDistance=Double.MAX_VALUE;
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                if (points.get(i).getDistance(points.get(j)) <= min.minDistance) {
                    min=points.get(i);
                    min.minDistance = points.get(i).getDistance(points.get(j));
                    min.Closest=points.get(j);
                }
            }
        }

        return min;
    }


}


class Point {
    long x;
    long y;
    double minDistance;
    Point Closest;

    public Point(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public double getDistance(Point point) {
        long xdis = this.x - point.x;
        long ydis = this.y - point.y;
        return Math.sqrt(xdis * xdis + ydis * ydis);

    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}


class MergeSort {
    ArrayList<Point> merge(List<Point> L, List<Point> R) {
        int n1 = L.size();
        int n2 = R.size();

        int i = 0, j = 0;

        ArrayList<Point> newPoints = new ArrayList<>();
        while (i < n1 && j < n2) {
            if (L.get(i).x <= R.get(j).x) {
                newPoints.add(L.get(i));
                i++;
            } else {
                newPoints.add(R.get(j));
                j++;
            }
        }
        while (i < n1) {
            newPoints.add(L.get(i));
            i++;
        }
        while (j < n2) {
            newPoints.add(R.get(j));
            j++;
        }
        return newPoints;
    }

    List<Point> sort(List<Point> points) {
        if (points.size() > 1) {
            int m = points.size() / 2;
            List<Point> leftPoints = sort(points.subList(0, m));
            List<Point> rightPoints = sort(points.subList(m, points.size()));

            ArrayList<Point> merged = merge(leftPoints, rightPoints);
            return merged;
        } else {
            return points;
        }

    }

}
