package mh.dev.blog.multi.common.repository;

import mh.dev.blog.multi.common.model.Test;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends CrudRepository<Test, Long> {
}
