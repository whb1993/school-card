/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SchoolCardTestModule } from '../../../test.module';
import { PressInfComponent } from 'app/entities/press-inf/press-inf.component';
import { PressInfService } from 'app/entities/press-inf/press-inf.service';
import { PressInf } from 'app/shared/model/press-inf.model';

describe('Component Tests', () => {
    describe('PressInf Management Component', () => {
        let comp: PressInfComponent;
        let fixture: ComponentFixture<PressInfComponent>;
        let service: PressInfService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SchoolCardTestModule],
                declarations: [PressInfComponent],
                providers: []
            })
                .overrideTemplate(PressInfComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PressInfComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PressInfService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PressInf(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.pressInfs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
