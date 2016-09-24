using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebApp.Web
{
    public class BoundingBox
    {
        public double topRightLatitude;
        public double topRightLongitude;
        public double bottomLeftLatitude;
        public double bottomLeftLongitude;

        public BoundingBox(double topRightLatitude, double topRightLongitude, double bottomLeftLatitude, double bottomLeftLongitude)
        {
            this.topRightLatitude = topRightLatitude;
            this.topRightLongitude = topRightLongitude;
            this.bottomLeftLatitude = bottomLeftLatitude;
            this.bottomLeftLongitude = bottomLeftLongitude;
        }
    }
}
