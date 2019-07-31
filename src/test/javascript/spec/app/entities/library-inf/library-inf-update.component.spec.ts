/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SchoolCardTestModule } from '../../../test.module';
import { LibraryInfUpdateComponent } from 'app/entities/library-inf/library-inf-update.component';
import { LibraryInfService } from 'app/entities/library-inf/library-inf.service';
import { LibraryInf } from 'app/shared/model/library-inf.model';

describe('Component Tests', () => {
    describe('LibraryInf Management Update Component', () => {
        let comp: LibraryInfUpdateComponent;
        let fixture: ComponentFixture<LibraryInfUpdateComponent>;
        let service: LibraryInfService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SchoolCardTestModule],
                declarations: [LibraryInfUpdateComponent]
            })
                .overrideTemplate(LibraryInfUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LibraryInfUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LibraryInfService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new LibraryInf(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.libraryInf = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new LibraryInf();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.libraryInf = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
