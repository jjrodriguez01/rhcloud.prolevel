/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.correo;

/**
 *
 * @author Correo
 * @author Rodrigo Aranda Fernandez
 */
import java.security.Security;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import utilidades.MiExcepcion;

/**
 * @author @date
 *
 */
public class Correo {

    /**
     * @param titulo : titulo del mensaje
     * @param mensaje : Cuerpo del Mensaje
     * @param paraEmail : Email receptor del mensaje
     * @return true si el envío es conforme y false si no es así.
     */
    public static synchronized boolean sendMail(String titulo, String mensaje, String paraEmail)throws MiExcepcion {
        boolean envio = false;

        try {

   //carga del archivo smtp.properties
            Properties propiedad = new Properties();
            //se leen el archivo .properties

            final ResourceBundle props = ResourceBundle.getBundle("controlador.correo.ConfigMail");

            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

            //Propiedades de la conexion
            Properties propiedades = new Properties();
            propiedades.setProperty("mail.transport.protocol", props.getString("mail.transport.protocol"));
            propiedades.setProperty("mail.host", props.getString("mail.host"));
            propiedades.put("mail.smtp.auth", props.getString("mail.smtp.auth"));
            propiedades.put("mail.smtp.port", props.getString("mail.smtp.port"));
            propiedades.put("mail.smtp.socketFactory.port", props.getString("mail.smtp.socketFactory.port"));
            propiedades.put("mail.smtp.socketFactory.class", props.getString("mail.smtp.socketFactory.class"));
            propiedades.put("mail.smtp.socketFactory.fallback", props.getString("mail.smtp.socketFactory.fallback"));
            propiedades.put("mail.smtp.mail.sender", props.getString("mail.smtp.mail.sender"));

            propiedades.setProperty("mail.smtp.quitwait", props.getString("mail.smtp.quitwait"));

            //Preparamos la Sesion autenticando al usuario
            Session session = Session.getDefaultInstance(propiedades, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(props.getString("mail.user"), props.getString("mail.password"));
                }
            });

            //Preparamos el Mensaje
            MimeMessage message = new MimeMessage(session);
            message.setSender(new InternetAddress(props.getString("mail.email")));
            message.setSubject(titulo);
            message.setContent("<html><head>"
                    + "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css\">"
                    + "<style>"
                    + "body{"
                    + "font-famyly: 'times new roman', 'arial narrow', verdana, arial;"
                    + "}"
                    + "div.page-header h1{"
                    + "color: green;"
                    + "}"
                    + "</style>"
                    + "</head>"
                    + "<body>"
                    + "<div class='page-header'>"
                    + "<h1>Pro-level</h1>"
                    + "</div>"
                    + "<hr/>"+mensaje
                    + "</body>"
                    + "</html>", "text/html; charset=utf-8");
            message.setFrom(new InternetAddress(props.getString("mail.smtp.mail.sender")));
            message.setReplyTo(InternetAddress.parse(props.getString("mail.smtp.mail.sender")));

            if (paraEmail.indexOf(',') > 0) {
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(paraEmail));
            } else {
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(paraEmail));
            }

            //envío del mensaje
            Transport.send(message);
            envio = true;

        } catch (Throwable mie) {
            envio = false;
            throw new MiExcepcion("Error al enviar Correos", mie);
        } finally {
            return envio;
        }
    }
}
