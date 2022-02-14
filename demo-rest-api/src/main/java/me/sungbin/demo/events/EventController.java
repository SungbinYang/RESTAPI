package me.sungbin.demo.events;

import lombok.RequiredArgsConstructor;
import me.sungbin.demo.App;
import me.sungbin.demo.common.ErrorsResource;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * packageName : me.sungbin.demo.events
 * fileName : EventController
 * author : rovert0
 * date : 2022/02/06
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/06       rovert         최초 생성
 */

@RestController
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class EventController {

    private final EventRepository eventRepository;

    private final ModelMapper modelMapper;

    private final EventValidator eventValidator;

    @PostMapping
    public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return badRequest(bindingResult);
        }

        eventValidator.validate(eventDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return badRequest(bindingResult);
        }

        Event event = modelMapper.map(eventDto, Event.class);
        event.update();
        Event newEvent = this.eventRepository.save(event);
        ControllerLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(newEvent.getId());
        URI createdUri = selfLinkBuilder.toUri();

        EventResource eventResource = new EventResource(event);
        eventResource.add(linkTo(EventController.class).withRel("query-events"));
        eventResource.add(selfLinkBuilder.withRel("update-event"));
        eventResource.add(linkTo(App.class).slash("docs/index.html#resources-events-create").withRel("profile"));
        return ResponseEntity.created(createdUri).body(eventResource);
    }

    @GetMapping
    public ResponseEntity queryEvents(Pageable pageable, PagedResourcesAssembler<Event> assembler) {
        Page<Event> page = this.eventRepository.findAll(pageable);
        PagedResources<Resource<Event>> pagedResources = assembler.toResource(page, entity -> new EventResource(entity));
        pagedResources.add(linkTo(App.class).slash("docs/index.html#resources-query-events-list").withRel("profile"));
        return ResponseEntity.ok(pagedResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity getEvent(@PathVariable Integer id) {
        Optional<Event> optionalEvent = this.eventRepository.findById(id);
        if (optionalEvent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Event event = optionalEvent.get();
        EventResource eventResource = new EventResource(event);
        eventResource.add(linkTo(App.class).slash("docs/index.html#resources-events-get").withRel("profile"));
        return ResponseEntity.ok(eventResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateEvent(@PathVariable Integer id, @RequestBody @Valid EventDto eventDto, BindingResult bindingResult) {
        Optional<Event> optionalEvent = this.eventRepository.findById(id);

        if (optionalEvent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (bindingResult.hasErrors()) {
            return badRequest(bindingResult);
        }

        this.eventValidator.validate(eventDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return badRequest(bindingResult);
        }

        Event existingEvent = optionalEvent.get();
        this.modelMapper.map(eventDto, existingEvent);
        Event savedEvent = this.eventRepository.save(existingEvent);

        EventResource eventResource = new EventResource(savedEvent);
        eventResource.add(linkTo(App.class).slash("docs/index.html#resources-events-update").withRel("profile"));

        return ResponseEntity.ok(eventResource);
    }

    private ResponseEntity<ErrorsResource> badRequest(BindingResult bindingResult) {
        return ResponseEntity.badRequest().body(new ErrorsResource(bindingResult));
    }
}
