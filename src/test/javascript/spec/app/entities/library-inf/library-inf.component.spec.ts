/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SchoolCardTestModule } from '../../../test.module';
import { LibraryInfComponent } from 'app/entities/library-inf/library-inf.component';
import { LibraryInfService } from 'app/entities/library-inf/library-inf.service';
import { LibraryInf } from 'app/shared/model/library-inf.model';

describe('Component Tests', () => {
    describe('LibraryInf Management Component', () => {
        let comp: LibraryInfComponent;
        let fixture: ComponentFixture<LibraryInfComponent>;
        let service: LibraryInfService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SchoolCardTestModule],
                declarations: [LibraryInfComponent],
                providers: []
            })
                .overrideTemplate(LibraryInfComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LibraryInfComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LibraryInfService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new LibraryInf(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.libraryInfs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
