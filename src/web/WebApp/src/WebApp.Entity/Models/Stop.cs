using System;

namespace WebApp.Entity.Models
{
    public class Stop
    {
        public int Id { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set; }
        public StopType Type { get; set; }
    }
}

[Flags]
public enum StopType
{
    Unknown = 0,
    Bus = 1,
    Tram = 1 << 1
}
