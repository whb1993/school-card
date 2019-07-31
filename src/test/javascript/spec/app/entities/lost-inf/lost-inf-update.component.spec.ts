/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SchoolCardTestModule } from '../../../test.module';
import { LostInfUpdateComponent } from 'app/entities/lost-inf/lost-inf-update.component';
import { LostInfService } from 'app/entities/lost-inf/lost-inf.service';
import { LostInf } from 'app/shared/model/lost-inf.model';

describe('Component Tests', () => {
    describe('LostInf Management Update Component', () => {
        let comp: LostInfUpdateComponent;
        let fixture: ComponentFixture<LostInfUpdateComponent>;
        let service: LostInfService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SchoolCardTestModule],
                declarations: [LostInfUpdateComponent]
            })
                .overrideTemplate(LostInfUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LostInfUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LostInfService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new LostInf(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.lostInf = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new LostInf();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.lostInf = entity;
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
