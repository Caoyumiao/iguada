package life.iGuaDa.community.service;

import life.iGuaDa.community.mapper.NavMapper;
import life.iGuaDa.community.model.Nav;
import life.iGuaDa.community.model.NavExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NavService {
    @Autowired
    private NavMapper navMapper;

    public List<Nav> list() {
        NavExample navExample = new NavExample();
        navExample.createCriteria()
                .andStatusEqualTo(1);
        navExample.setOrderByClause("priority desc");
        return navMapper.selectByExample(navExample);
    }
}
