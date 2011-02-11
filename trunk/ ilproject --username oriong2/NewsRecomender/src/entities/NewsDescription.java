/**
 * HouseDescription.java
 * jCOLIBRI2 framework. 
 * @author Juan A. Recio-García.
 * GAIA - Group for Artificial Intelligence Applications
 * http://gaia.fdi.ucm.es
 * 26/10/2007
 */
package entities;



import dominio.Category;
import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;

/**
 * Implements the house description.
 * @author Juan A. Recio-Garcia
 * @version 1.0
 *
 */
public class NewsDescription implements CaseComponent
{
	Integer id;
	public String sUrl;
	public float cat1;
	public float cat2;
	public float cat3;
	public float cat4;
	public float cat5;
	public float cat6;
	public float cat7;
	public float cat8;
	public float cat9;
	public float cat10;
	public float cat11;
	public float cat12;
	public float cat13;
	public float cat14;
	public float cat15;
	public float cat16;
	public float cat17;
	public float cat18;
	public float cat19;
	public float cat20;
	public float cat21;
	public float cat22;
	public float cat23;
	public float cat24;
	public float cat25;
	public float cat26;
	public float cat27;
	public float cat28;
	public float cat29;
	public float cat30;
	public float cat31;
	public float cat32;
	public float cat33;
	public float cat34;
	public float cat35;
	public float cat36;
	public float cat37;
	public float cat38;
	public float cat39;
	public float cat40;
	public float cat41;
	public float cat42;
	public float cat43;
	public float cat44;
	public float cat45;
	public float cat46;
	public float cat47;
	public float cat48;
	public float cat49;
	
	
	

    
    public String toString() 
    {
		return sUrl;
    }
    

    public Integer getId()
    {
        return id;
    }


    public void setId(Integer id)
    {
        this.id = id;
    }

    /* (non-Javadoc)
     * @see jcolibri.cbrcore.CaseComponent#getIdAttribute()
     */
    public Attribute getIdAttribute()
    {
	return new Attribute("id",this.getClass());
    }

}
