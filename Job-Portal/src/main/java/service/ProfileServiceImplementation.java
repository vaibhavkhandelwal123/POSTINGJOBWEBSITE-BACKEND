package service;

import dto.ProfileDTO;
import entity.Profile;
import exception.JobPortalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ProfileRepository;
import utility.Utilities;

import java.util.ArrayList;
import java.util.List;

@Service("profileService")
public class ProfileServiceImplementation implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Override
    public Long createProfile(String email) throws Exception {
        Profile profile = new Profile();
        profile.setId(Utilities.getNextSequence("profiles"));
        profile.setEmail(email);
        profile.setSkills(new ArrayList<>());
        profile.setExperiences(new ArrayList<>());
        profile.setCertifications(new ArrayList<>());
        profileRepository.save(profile);
        return profile.getId();
    }

    @Override
    public ProfileDTO getProfile(Long id) throws JobPortalException {
        return profileRepository.findById(id).orElseThrow(()->new JobPortalException("Profile not found")).toDTO();
    }

    @Override
    public ProfileDTO updateProfile(ProfileDTO profileDTO) throws JobPortalException {
        profileRepository.findById(profileDTO.getId()).orElseThrow(()->new JobPortalException("Profile not found"));
        profileRepository.save(profileDTO.toEntity());
        return profileDTO;
    }

    @Override
    public List<ProfileDTO> getAllProfiles() throws JobPortalException {
        return profileRepository.findAll().stream().map((x)->x.toDTO()).toList();
    }
}
