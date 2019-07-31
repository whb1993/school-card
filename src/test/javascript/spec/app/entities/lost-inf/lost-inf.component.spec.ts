/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SchoolCardTestModule } from '../../../test.module';
import { LostInfComponent } from 'app/entities/lost-inf/lost-inf.component';
import { LostInfService } from 'app/entities/lost-inf/lost-inf.service';
import { LostInf } from 'app/shared/model/lost-inf.model';

describe('Component Tests', () => {
    describe('LostInf Management Component', () => {
        let comp: LostInfComponent;
        let fixture: ComponentFixture<LostInfComponent>;
        let service: LostInfService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SchoolCardTestModule],
                declarations: [LostInfComponent],
                providers: []
            })
                .overrideTemplate(LostInfComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LostInfComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LostInfService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new LostInf(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.lostInfs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
