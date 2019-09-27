package org.superbiz.struts.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.superbiz.struts.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
