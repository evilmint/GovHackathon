using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Metadata;

namespace WebApp.Web.Data.Migrations
{
    public partial class addschools : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Schools",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn),
                    Address = table.Column<string>(nullable: true),
                    Description = table.Column<string>(nullable: true),
                    KlasaWielk = table.Column<string>(nullable: true),
                    KodPoczt = table.Column<string>(nullable: true),
                    Latitude = table.Column<double>(nullable: false),
                    Longitude = table.Column<double>(nullable: false),
                    Miejscowość = table.Column<string>(nullable: true),
                    Name = table.Column<string>(nullable: true),
                    NrDomu = table.Column<string>(nullable: true),
                    Patron = table.Column<string>(nullable: true),
                    Poczta = table.Column<string>(nullable: true),
                    Polgm = table.Column<string>(nullable: true),
                    Polpow = table.Column<string>(nullable: true),
                    Polwoj = table.Column<string>(nullable: true),
                    Publiczność = table.Column<string>(nullable: true),
                    Telefon = table.Column<string>(nullable: true),
                    Typ = table.Column<string>(nullable: true),
                    Ulica = table.Column<string>(nullable: true),
                    Www = table.Column<string>(nullable: true),
                    Złożoność = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Schools", x => x.Id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Schools");
        }
    }
}
