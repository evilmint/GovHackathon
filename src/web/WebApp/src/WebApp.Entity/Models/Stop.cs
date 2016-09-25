using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace WebApp.Entity.Models
{
    public class Stop
    {
        public int Id { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set; }
        public StopType Typ { get; set; }

        [NotMapped]
        public string Type => "stop";

        public double Distance(double latitude, double longitude)
        {
            return Web.Entity.Distance.Compute(this.Latitude, this.Longitude, latitude, longitude);
        }
    }
}

[Flags]
public enum StopType
{
    Unknown = 0,
    Bus = 1,
    Tram = 1 << 1
}
