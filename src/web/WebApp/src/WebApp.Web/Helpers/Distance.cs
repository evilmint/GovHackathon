using System;

namespace WebApp.Web.Helpers
{
    public static class Distance
    {
        public static double Compute(double firstLatitude, double firstLongitude, double secondLatitude, double secondLongitude)
        {
            return Math.Sqrt(Math.Abs(firstLatitude - secondLatitude) + Math.Abs(firstLongitude - secondLongitude)) * 60.71;
        }
    }
}
