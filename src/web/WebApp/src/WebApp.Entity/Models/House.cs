using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebApp.Entity.Models
{
    public class House
    {
        public int Id { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set; }
        public double Price { get; set; }
        public House.TypeEnum Type { get; set; }
        public int RoomCount { get; set; }
        public int Area { get; set; }

        public enum TypeEnum
        {
            Flat, StandaloneHouse
        }
    }
}
