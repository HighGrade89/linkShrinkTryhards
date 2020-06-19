package com.example.linkshrink.repo;

import com.example.linkshrink.entity.Weblink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface WeblinkRepo extends JpaRepository<Weblink, Long> {
    Weblink findWeblinkByFullUrl(String fullLink);
    Weblink findWeblinkByShortUrlSuffix(String shrinkedLink);
    Weblink getWeblinkById(long id);
}
