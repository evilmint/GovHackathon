using Microsoft.EntityFrameworkCore.Migrations;

namespace WebApp.Web.Data.Migrations
{
    public partial class FixedTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "Type",
                table: "Stops",
                newName: "Typ");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "Typ",
                table: "Stops",
                newName: "Type");
        }
    }
}
