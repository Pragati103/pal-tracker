package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


import static org.springframework.http.ResponseEntity.*;
@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    @Autowired
    private  TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository=timeEntryRepository;
    }

    @PostMapping()
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        long projectId = 123L;
        long userId = 456L;
        TimeEntry timeEntry= new TimeEntry(0, projectId, userId, LocalDate.parse("2017-01-08"), 8);
        TimeEntry createResponse=this.timeEntryRepository.create(timeEntry);
        return new ResponseEntity<>(createResponse,HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry timeEntry1=  new TimeEntry(0L, 123L, 456L, LocalDate.parse("2017-01-08"), 8);
        this.timeEntryRepository.create(timeEntry1);
        TimeEntry timeEntry =timeEntryRepository.find(id);
        if (timeEntry!=null) {
            return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        }




    @GetMapping()
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> list= timeEntryRepository.list();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable long id, @RequestBody TimeEntry timeEntryToUpdate) {

        TimeEntry res=timeEntryRepository.update(id,timeEntryToUpdate);
        if (res!=null) {
            return new ResponseEntity<TimeEntry>(res, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        timeEntryRepository.delete(id);

        return noContent().build();
    }
}
