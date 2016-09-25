using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace WebApp.Entity.Models
{
    public class Event
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Url { get; set; }
        public string Description { get; set; }
        public string Typ { get; set; }
        public DateTime StartDate { get; set; }
        public DateTime EndDate { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set; }

        [NotMapped]
        public string Type => "event";
    }
}