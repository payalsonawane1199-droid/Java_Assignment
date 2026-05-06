import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentValidator {

    private static final Logger logger = LoggerFactory.getLogger(DocumentValidator.class);

    public ValidationResult validate(Document doc) {
        try {
            if (doc == null) {
                // FIX: handle expected validation failure safely
                logger.warn("Document is null");
                return new ValidationResult(false);
            }

            String content = doc.extractContent();

            if (content == null || content.isEmpty()) {
                // FIX: handle validation case instead of throwing exception
                logger.warn("Empty content");
                return new ValidationResult(false);
            }

            return runValidationRules(content);

        } catch (Exception e) {
            // FIX: proper logging instead of printStackTrace
            logger.error("Unexpected error during validation", e);

            // FIX: avoid null return
            return new ValidationResult(false);
        }
    }

    public void validateBatch(List<Document> docs) {
        for (Document doc : docs) {
            try {
                ValidationResult r = validate(doc);

                // FIX: null safety check
                if (r != null && r.isValid()) {
                    saveResult(r);
                }

            } catch (Exception e) {
                // FIX: do not swallow exception
                logger.error("Error in batch validation", e);
            }
        }
    }

    
    private ValidationResult runValidationRules(String content) {
        return new ValidationResult(true);
    }

    private void saveResult(ValidationResult r) {
    }
}
