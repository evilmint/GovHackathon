using System.ComponentModel.DataAnnotations.Schema;

namespace WebApp.Entity.Models
{
    public class Point
    {
        public int Id { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set; }
        [NotMapped]
        public double Score { get; set; }
    }
}
