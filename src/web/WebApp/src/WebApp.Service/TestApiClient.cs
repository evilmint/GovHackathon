using RestSharp;

namespace WebApp.Service
{
    public static class TestApiClient
    {
        public static string Test()
        {
            var client = new RestClient("http://jsonplaceholder.typicode.com/");
            // client.Authenticator = new HttpBasicAuthenticator(username, password);

            var request = new RestRequest("posts/{id}", Method.GET);
            //request.AddParameter("name", "value"); // adds to POST or URL querystring based on Method
            request.AddUrlSegment("id", "1"); // replaces matching token in request.Resource

            IRestResponse response = client.Execute(request);

            return response.Content;
        }
    }
}