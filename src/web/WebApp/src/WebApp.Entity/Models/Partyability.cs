using System.ComponentModel.DataAnnotations.Schema;

namespace WebApp.Entity.Models
{
    public class Partyability
    {
        public int Id { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set; }
        public string Month { get; set; }
        public double Value { get; set; }

        [NotMapped]
        public string Type => "partyability";
    }
}
