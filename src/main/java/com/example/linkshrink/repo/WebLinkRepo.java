package com.example.linkshrink.repo;

import com.example.linkshrink.entity.Weblink;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface WebLinkRepo extends CrudRepository<Weblink, Long> {
    Weblink findWeblinkByFullUrl(String fullLink);
    Weblink findWeblinkByShortUrlSuffix(String shrinkedLink);
    Weblink getWeblinkById(long id);
}
