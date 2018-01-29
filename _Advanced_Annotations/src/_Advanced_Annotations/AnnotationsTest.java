package _Advanced_Annotations;

import java.lang.annotation.*;

import com.sun.istack.internal.NotNull;

@Documented
@interface ClassPreamble {
	@NotNull String author();
	@NotNull String date();
	int currentRevision() default 1;
	String lastModified() default "N/A";
	String lastModifiedBy() default "N/A";
	
	// Note use of array
	@NotNull String[] reivewers();
}

@ClassPreamble(author = "Radoslav", date = "01/29/2018", reivewers = { "rradoev" })
/**
 * This is a stupid class that does nothing
 * @author superlamer
 *
 */
public class AnnotationsTest  {

	
}
