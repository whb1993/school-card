/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SchoolCardTestModule } from '../../../test.module';
import { FillInfUpdateComponent } from 'app/entities/fill-inf/fill-inf-update.component';
import { FillInfService } from 'app/entities/fill-inf/fill-inf.service';
import { FillInf } from 'app/shared/model/fill-inf.model';

describe('Component Tests', () => {
    describe('FillInf Management Update Component', () => {
        let comp: FillInfUpdateComponent;
        let fixture: ComponentFixture<FillInfUpdateComponent>;
        let service: FillInfService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SchoolCardTestModule],
                declarations: [FillInfUpdateComponent]
            })
                .overrideTemplate(FillInfUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FillInfUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FillInfService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new FillInf(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.fillInf = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new FillInf();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.fillInf = entity;
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
