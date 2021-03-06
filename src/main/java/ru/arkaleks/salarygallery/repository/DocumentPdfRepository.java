package ru.arkaleks.salarygallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arkaleks.salarygallery.model.DocumentPdf;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface DocumentPdfRepository extends JpaRepository<DocumentPdf, Integer> {
}
