public ValidationResult validate(Document doc) {
    try {
        if (doc == null) {
            // FIX: use IllegalArgumentException for expected validation failure instead of generic RuntimeException
            throw new IllegalArgumentException("Document is null");
        }
        String content = doc.extractContent();
        if (content == null || content.isEmpty()) {
            // FIX: treat empty content as expected validation failure and also handle null content safely
            throw new IllegalArgumentException("Empty content");
        }
        return runValidationRules(content);

    } catch (IllegalArgumentException e) {
        // FIX: avoid printStackTrace for expected validation failures; log concise warning instead
        logger.warn("Validation failed: {}", e.getMessage());
        return ValidationResult.invalid(e.getMessage());

    } catch (Exception e) {
        // FIX: do not return null on unexpected errors; log full error and return invalid result
        logger.error("Unexpected validation error", e);
        return ValidationResult.invalid("Unexpected validation error");
    }
}

public void validateBatch(List<Document> docs) {
    for (Document doc : docs) {
        try {
            ValidationResult r = validate(doc);
            // FIX: guard against invalid/null results before calling isValid to avoid silent batch failures
            if (r != null && r.isValid()) {
                saveResult(r);
            }
        } catch (Exception e) {
            // FIX: do not swallow batch exceptions; log them so real processing issues are traceable
            logger.error("Batch validation failed for document", e);
        }
    }
}