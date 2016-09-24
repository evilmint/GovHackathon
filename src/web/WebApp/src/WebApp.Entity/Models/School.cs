using System.ComponentModel.DataAnnotations.Schema;

namespace WebApp.Entity.Models
{
    public partial class School
    {
        public int Id { get; set; }
        public string Polwoj { get; set; }
        public string Polpow { get; set; }
        public string Polgm { get; set; }
        public string Miejscowość { get; set; }
        public string KlasaWielk { get; set; }
        public string Typ { get; set; }
        public string Złożoność { get; set; }
        public string Patron { get; set; }
        public string Ulica { get; set; }
        public string NrDomu { get; set; }
        public string KodPoczt { get; set; }
        public string Poczta { get; set; }
        public string Telefon { get; set; }
        public string Www { get; set; }
        public string Publiczność { get; set; }
        public string Name { get; set; }
        public double Longitude { get; set; }
        public double Latitude { get; set; }
        public string Description { get; set; }
        public string Address { get; set; }

        [NotMapped]
        public string Type
        {
            get
            {
                switch (this.Typ)
                {
                    case "00001":
                        return "preschool";

                    case "00003":
                        return "primaryschool";

                    case "00004":
                        return "middleschool";
                }
                return "otherschool";
            }
        }
    }
}