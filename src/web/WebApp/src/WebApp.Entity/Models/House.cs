using System.ComponentModel.DataAnnotations.Schema;

namespace WebApp.Entity.Models
{
    public class House
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set; }
        public double Price { get; set; }
        public House.TypeEnum Type { get; set; }
        public int RoomCount { get; set; }
        public int Area { get; set; }
        public string ImageUrl { get; set; }
        [NotMapped]
        public double Score { get; set; }

        public enum TypeEnum
        {
            Flat, StandaloneHouse
        }
    }
}
