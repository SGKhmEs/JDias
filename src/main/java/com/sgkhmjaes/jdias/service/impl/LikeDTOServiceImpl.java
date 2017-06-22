package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Like;
import com.sgkhmjaes.jdias.repository.LikeRepository;
import com.sgkhmjaes.jdias.service.dto.AuthorDTO;
import com.sgkhmjaes.jdias.service.dto.AvatarDTO;
import com.sgkhmjaes.jdias.service.dto.LikeDTO;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LikeDTOServiceImpl {

    private final Logger log = LoggerFactory.getLogger(LikeDTOServiceImpl.class);
    private final LikeRepository likeRepository;
    private final AvatarDTOServiceImpl avatarDTOServiceImpl;

    public LikeDTOServiceImpl(LikeRepository likeRepository, AvatarDTOServiceImpl avatarDTOServiceImpl) {
        this.avatarDTOServiceImpl = avatarDTOServiceImpl;
        this.likeRepository = likeRepository;
    }

    public List<LikeDTO> findAllByPostId(Long id) {
        log.debug("Method LikeDTOServiceImpl.findAllByPostId : id=", id);
        List<Like> likeList = likeRepository.findAllByPostId(id);
        List<LikeDTO> likeDTOList = new ArrayList<>();
        likeList.forEach((like) -> {likeDTOList.add(createLikeDTOfromLike(like));});
        return likeDTOList;
    }
    
    public List<LikeDTO> findAll() {
        List<Like> likeList = likeRepository.findAll();
        log.debug("Method LikeDTOServiceImpl.findAllByPostId : likiSize=", likeList.size());
        List<LikeDTO> likeDTOList = new ArrayList<>();
        likeList.forEach((like) -> {likeDTOList.add(createLikeDTOfromLike(like));});
        return likeDTOList;
    }
    
    private LikeDTO createLikeDTOfromLike (Like like) {
            AvatarDTO avatarDTO = avatarDTOServiceImpl.findOne(like.getPerson().getId()); 
            AuthorDTO authorDTO = new AuthorDTO();
            LikeDTO likeDTO = new LikeDTO();
            try {
                authorDTO.mappingToDTO(like.getPerson(), avatarDTO);
                likeDTO.mappingToDTO(like, authorDTO);
            } catch (InvocationTargetException ex) {
                java.util.logging.Logger.getLogger(LikeDTOServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            return likeDTO;
    }
    
}
