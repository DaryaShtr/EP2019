import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/solve")
public class SolveServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String resultString;
        String startString = "";
        try {

            startString = req.getParameter("input");

            if(!startString.matches("[\\s]*-?[0-9]+([.][0-9]+)?[\\s]+[+\\-*/][\\s]+-?[0-9]+([.][0-9]+)?[\\s]*")) {
                resultString = "incorrect input data";
            } else {
                double result;
                String[] arr = startString.trim().split("\\s+");
                double num1 = Double.parseDouble(arr[0]);
                double num2 = Double.parseDouble(arr[2]);
                String sign = arr[1];
                switch (sign) {
                    case "+" :
                        result = num1 + num2;
                        break;
                    case "-" :
                        result = num1 - num2;
                        break;
                    case "*" :
                        result = num1 * num2;
                        break;
                    case "/" :
                        result = num1 / num2;
                        break;
                        default:
                            result = 0;
                }

                resultString = Double.toString(result);
            }




        } catch (Exception e) {
            resultString = e.toString();
        }

        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html; charset=Cp1251");

        out.print("<!DOCTYPE html>\n" +
                "<html>\n" +
                "\n" +
                "<head>\n" +
                "    <title>Calc</title>\n" +
                "    <link href='./resources/css/calc.css' type='text/css' rel='stylesheet'/>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<main>\n" +
                "    <form action=\"solve\">\n" +
                "        <input type=\"text\" name=\"input\" id=\"input\" value=\"" + startString + "\">\n" +
                "        <input type=\"submit\" id=\"bttn\" value=\"solve\">\n" +
                "        <div id=\"result\">" + resultString + "</div>\n" +
                "    </form>\n" +
                "</main>\n" +
                "\n" +
                "</body>\n" +
                "</html>");

        out.close();
    }
}
