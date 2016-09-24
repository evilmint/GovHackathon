﻿namespace WebApp.Entity.Models
{
    public class Relic
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set; }
        public string Categories { get; set; }
    }
}
