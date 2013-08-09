/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package werkko.data;

import java.util.ArrayList;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author lehtimik
 */
public class DrinkkiLomake extends Lomake{
    
    public DrinkkiLomake(){
     
}


   public void tutkiVirheita(){
  
  if (onkoMaaraAinesosaOK(ainesosa2, maara2) == false) {
            setErrorViesti("Tarkista ainesosa 2 ja m‰‰r‰");
            return;
        }
        if (onkoMaaraAinesosaOK(ainesosa3, maara3) == false) {
            setErrorViesti("Tarkista ainesosa 3 ja m‰‰r‰");
            return;
        }
        if (onkoMaaraAinesosaOK(ainesosa4, maara4) == false) {
            setErrorViesti("Tarkista ainesosa 4 ja m‰‰r‰");
            return;
        }
        if (onkoMaaraAinesosaOK(ainesosa5, maara5) == false) {
            setErrorViesti("Tarkista ainesosa 5 ja m‰‰r‰");

        }
  
   if(tyyppi_name.length()>15){
            setErrorViesti("Tyyppi pit‰‰ olla 2-15 merkki‰ pitk‰");
      }
          if(tyyppi_name.length()<2){
            setErrorViesti("Tyyppi pit‰‰ olla 2-15 merkki‰ pitk‰");
      }
   }
    
    


   
}
