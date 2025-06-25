package service;

import dto.ProfileDTO;
import exception.JobPortalException;

public interface ProfileService {
    public Long createProfile(String email) throws Exception;
    public ProfileDTO getProfile(Long id) throws JobPortalException;
    public ProfileDTO updateProfile(ProfileDTO profileDTO) throws JobPortalException;


}
