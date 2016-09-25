using System;

namespace WebApp.Web.Helpers
{
    public static class Distance
    {
        public static double Compute(double firstLatitude, double firstLongitude, double secondLatitude, double secondLongitude)
        {
            return Math.Sqrt(Math.Pow(firstLatitude - secondLatitude, 2) + Math.Pow(firstLongitude - secondLongitude, 2)) * 60.71;
        }
    }
}
