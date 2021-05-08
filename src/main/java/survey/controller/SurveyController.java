package survey.controller;

import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import survey.repository.OptionRepository;
import survey.repository.SurveyRepository;
import survey.repository.VoterRepository;
import survey.security.jwt.JwtUtils;
import survey.exception.UnauthorizedException;
import survey.model.*;
import survey.payload.NewSurveyRequest;
import survey.payload.DeleteSurvey;
import survey.payload.NewOption;
import survey.payload.NewVoter;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1/surveys")
public class SurveyController {
	
	@Autowired
	private SurveyRepository surveyRepository;
	
	@Autowired 
	private OptionRepository optionRepository;
	
	@Autowired
	private VoterRepository voterRepository;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/all")
	public List<Survey> getAllSurveys() {
		return surveyRepository.findAll();
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{id}")
	public Optional<Survey> getSurveyById(@PathVariable Long id) {
		return surveyRepository.findById(id);
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/add")
	public Survey addSurvey(@Valid @RequestBody NewSurveyRequest surveyObject, @RequestHeader("Authorization") String token) {
		token = token.substring(7, token.length());
		String username = jwtUtils.getUserNameFromJwtToken(token);
		Set<Option> options = new HashSet<Option>();
		Survey survey = new Survey(
				surveyObject.getContent(),
				surveyObject.getAuthor(),
				options);
		if(!username.equals(surveyObject.getAuthor())) {
			throw new UnauthorizedException();
		}
		else {
			surveyRepository.save(survey);
			List<NewOption> surveyOptions = surveyObject.getOptions();
			for(NewOption surveyOption: surveyOptions) {
				Set<Voter> voters = new HashSet<Voter>();
				Option option = new Option(
						surveyOption.getContent(),
						(long) 0,
						survey.getId(),
						voters);
				optionRepository.save(option);
				options.add(option);
			}
			survey.setOptions(options);
		}
		return survey;
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/vote")
	public List<Survey> addVote(@Valid @RequestBody NewVoter voteObject, @RequestHeader("Authorization") String token) {
		System.out.println("JwtToken => " + token);
		token = token.substring(7, token.length());
		String username = jwtUtils.getUserNameFromJwtToken(token);
		System.out.println("username => " + username);
		Option option = null;
		if(!username.equals(voteObject.getName())) {
			throw new UnauthorizedException();
		}
		else {
			List<Voter> allVoters = new ArrayList<Voter>();
			allVoters = voterRepository.findAll();
			boolean isVoterPresent = false;
			boolean isSameOption = false;
			List<Option> options = new ArrayList<Option>();
			options = optionRepository.findAll();
			for(Option currOption:options) {
				if(currOption.getId() == voteObject.getOptionId()) {
					option = currOption;
					break;
				}
			}
			Set<Voter> voters = new HashSet<Voter>();
			voters = option.getVoters();
			Voter voter = null;
			for(Voter currVoter:allVoters) {
				System.out.println("currVoterName => " + currVoter.getName());
				System.out.println("currSurveyId => " + currVoter.getSurveyId());
				System.out.println("voteObjectName => " + voteObject.getName());
				System.out.println("voteObjectSurveyId => " + voteObject.getSurveyId());
				if(currVoter.getName().equals(voteObject.getName()) && currVoter.getSurveyId().equals(voteObject.getSurveyId())) {
					isVoterPresent = true;
					voter = currVoter;
					if(currVoter.getOptionId().equals(voteObject.getOptionId())) {
						isSameOption = true;
					}
					break;
				}
			}
			System.out.println("isVoterPresent => " + isVoterPresent);
			System.out.println("isSameOption => " + isSameOption);
			if(isVoterPresent) {
				System.out.println("voter name => " + voter.getName());
				System.out.println("voter Id => " + voter.getId());
				System.out.println("voter optionId => " + voter.getOptionId());
				System.out.println("voter surveyId => " + voter.getSurveyId());
				voterRepository.deleteById(voter.getId());
				if(isSameOption) {
					voters.remove(voter);
					option.setNumberOfVotes(voters.size());
				}
				else {
					Voter newVoter = new Voter(
							voteObject.getName(),
							voteObject.getOptionId(),
							voteObject.getSurveyId()
							);
					voterRepository.save(newVoter);
					voters.add(newVoter);
				}
				optionRepository.save(option);
			}
			else {
				Voter newVoter = new Voter(
						voteObject.getName(),
						voteObject.getOptionId(),
						voteObject.getSurveyId()
						);
				voterRepository.save(newVoter);
				voters.add(newVoter);
				option.setNumberOfVotes(voters.size());
				optionRepository.save(option);
			}
		}
		return surveyRepository.findAll();
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/delete")
	public void deleteSurvey(@Valid @RequestBody DeleteSurvey surveyObject, @RequestHeader("Authorization") String token) {
		token = token.substring(7, token.length());
		String username = jwtUtils.getUserNameFromJwtToken(token);
		System.out.println("author => " + surveyObject.getUsername());
		System.out.println("Token username => " + username);
		if(!username.equals(surveyObject.getUsername())) {
			throw new UnauthorizedException();
		}
		else {
			surveyRepository.deleteById(surveyObject.getSurveyId());
		}
	}
	
}
