/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SchoolCardTestModule } from '../../../test.module';
import { PressInfUpdateComponent } from 'app/entities/press-inf/press-inf-update.component';
import { PressInfService } from 'app/entities/press-inf/press-inf.service';
import { PressInf } from 'app/shared/model/press-inf.model';

describe('Component Tests', () => {
    describe('PressInf Management Update Component', () => {
        let comp: PressInfUpdateComponent;
        let fixture: ComponentFixture<PressInfUpdateComponent>;
        let service: PressInfService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SchoolCardTestModule],
                declarations: [PressInfUpdateComponent]
            })
                .overrideTemplate(PressInfUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PressInfUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PressInfService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new PressInf(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.pressInf = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new PressInf();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.pressInf = entity;
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
